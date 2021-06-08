using MobileProjectSamsung.Application.Data;
using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using MobileProjectSamsung.Application.Exceptions;

namespace MobileProjectSamsung.Application.Services.CouponCreatorService
{
    public class CouponCreatorService : ICouponCreatorService
    {
        private readonly DataContext _dataContext;

        public CouponCreatorService(DataContext dataContext)
        {
            _dataContext = dataContext;
        }

        public async Task<CouponCreator> GetCouponCreatorByIdAsync(int id, bool withCoupons = false)
        {

            if (withCoupons)
            {
                return await _dataContext.CouponCreators.Where(c => c.Id == id)
                   .Include(c => c.Coupons)
                   .Include(c => c.UserCreator)
                   .SingleOrDefaultAsync();
            }
            else
            {
                return await _dataContext.CouponCreators.Where(c => c.Id == id)
                    .Include(c => c.UserCreator)
                    .SingleOrDefaultAsync();
            }
        }

        public async Task<CouponCreator> GetCouponCreatorByIdAndUserCreatorAsync(int id, string userCreator, bool withCoupons = false)
        {
            if (withCoupons)
            {
                return await _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator)
                   .Include(c => c.Coupons)
                   .Include(c => c.UserCreator)
                   .SingleOrDefaultAsync();
            }
            else
            {
                return await _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator)
                    .Include(c => c.UserCreator)
                    .SingleOrDefaultAsync();
            }
        }

        public async Task<List<CouponCreator>> GetCouponCreatorsByFirstIndexAndCountAsync(int startId, int count, double? xPosition, double? yPosition)
        {
            List<CouponCreator> coupons;
            if (xPosition == null && yPosition == null)
            {
                coupons = await _dataContext.CouponCreators.Where(c => c.Id >= startId 
                && c.TargetX == null 
                && c.TargetY == null 
                && c.Radius == null).Take(count).ToListAsync();
            }
            else if (xPosition == null || yPosition == null)
            {
                throw new LogicException("Некорректный ввод геолокации");
            }
            else
            {
                coupons = await _dataContext.CouponCreators.Where(c => c.Id >= startId).Where(c => c.TargetX != null
                && c.TargetY != null
                && c.Radius != null).ToListAsync();

                coupons = coupons.Where(c => CheckLocationConditionsOfUser(xPosition, yPosition, c.TargetX, c.TargetY, c.Radius)).Take(count).ToList();

                if (coupons.Count < count)
                {
                    var couponsWithNull = await _dataContext.CouponCreators.Where(c => c.Id >= startId
                    && c.TargetX == null && c.TargetY == null && c.Radius == null).Take(count - coupons.Count).ToListAsync();
                    coupons.AddRange(couponsWithNull);
                }
            }

            if (coupons.Count == 0)
            {
                throw new LogicException("Не удалось получить купоны");
            }
            return coupons;
        }

        public async Task<List<CouponCreator>> GetCouponCreatorsBySearchAndFirsIdAndCountAsync(int count, string search, string userName, string userRole, double? xPosition, double? yPosition)
        {
            List<CouponCreator> coupons;

            if ((search == "admin" || search == userName) && userRole == Role.Counterparty)
            {
                coupons = await GetUserCreatorCouponCreatorsAsync(userName, count);
                
                if (coupons.Count == 0)
                {
                    throw new LogicException("Вы ещё не создали предложений");
                }

                return coupons;
            }

            if (xPosition == null && yPosition == null)
            {
                coupons = await _dataContext.CouponCreators.Where(c => 
                c.Description.Contains(search.Trim())
                && c.TargetX == null
                && c.TargetY == null
                && c.Radius == null
                ).Take(count).ToListAsync();
            }
            else if (xPosition == null || yPosition == null)
            {
                throw new LogicException("Некорректный ввод геолокации");
            }
            else
            {
                coupons = await _dataContext.CouponCreators.Where(c => c.Description.Contains(search.Trim())).Where(c => c.TargetX != null
                && c.TargetY != null
                && c.Radius != null).ToListAsync();

                coupons = coupons.Where(c => CheckLocationConditionsOfUser(xPosition, yPosition, c.TargetX, c.TargetY, c.Radius)).Take(count).ToList();

                if (coupons.Count < count)
                {
                    var couponsWithNull = await _dataContext.CouponCreators.Where(c => c.Description.Contains(search.Trim())
                    && c.TargetX == null && c.TargetY == null && c.Radius == null).Take(count - coupons.Count).ToListAsync();
                    coupons.AddRange(couponsWithNull);
                }

            }
            if (coupons.Count == 0)
            {
                throw new LogicException("Не удалось получить купоны");
            }
            return coupons;
        }

        public async Task<List<CouponCreator>> GetUserCreatorCouponCreatorsAsync(string userName, int count)
        {
            return await _dataContext.CouponCreators.Where(c => c.UserCreatorUsername == userName).Take(count).ToListAsync();
        }

        public async Task<CouponCreator> RemoveCouponCreatorAsync(int id, string userCreator)
        {
            var couponCreator = await _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator).SingleOrDefaultAsync();

            if (couponCreator == null)
            {
                throw new LogicException("Выбранный купон не найден или уже удален");
            }

            _dataContext.CouponCreators.Remove(couponCreator);
            await _dataContext.SaveChangesAsync();

            return couponCreator;
        }

        public async Task<CouponCreator> ChangeCouponCreatorAsync(int id, double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreator)
        {
            if (!CheckLocationProperties(targetX, targetY, radius))
            {
                throw new LogicException("Необходимо ввести все параметры желаемой области");
            }

            var coupon = await GetCouponCreatorByIdAndUserCreatorAsync(id, userCreator);

            if (coupon == null)
            {
                throw new LogicException("Не удалось найти выбранное предложение");
            }

            coupon.TargetX = targetX;
            coupon.TargetY = targetY;
            coupon.Radius = radius;
            coupon.EndOfCoupon = endOfCoupon;
            coupon.Description = description;

            await _dataContext.SaveChangesAsync();

            return coupon;
        }

        public async Task<CouponCreator> AddCouponCreatorAsync(double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreatorName)
        {
            if (!CheckLocationProperties(targetX, targetY, radius))
            {
                throw new LogicException("Необходимо ввести все параметры желаемой области");
            }

            var userCreator = await _dataContext.Users.SingleOrDefaultAsync(u => u.Username == userCreatorName);

            if (userCreator == null)
            {
                throw new LogicException("У данного пользователя нет прав для создания предложения");
            }

            var couponCreator = new CouponCreator(targetX, targetY, radius, endOfCoupon, description, userCreator);

            _dataContext.CouponCreators.Add(couponCreator);
            await _dataContext.SaveChangesAsync();

            return couponCreator;
        }

        public bool CheckLocationProperties(double? targetX, double? targetY, double? radius)
        {
            if (targetX == null && targetY == null && radius == null)
            {
                return true;
            }
            else if (targetX != null && targetY != null && radius != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private bool CheckLocationConditionsOfUser(double? userX, double? userY, double? targetX, double? targetY, double? targetRadius)
        {
            return Math.Pow(((double)userX - (double)targetX), 2) + Math.Pow(((double)userY - (double)targetY), 2) <= Math.Pow((double)targetRadius, 2);
        }
    }
}
