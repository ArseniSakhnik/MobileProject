using MobileProjectSamsung.Application.Data;
using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using MobileProjectSamsung.Application.Services.UserService;
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

        public CouponCreator GetCouponCreatorById(int id, bool withCoupons = false)
        {
            CouponCreator couponCreator = null;

            if (withCoupons)
            {
                couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id)
                   .Include(c => c.Coupons)
                   .Include(c => c.UserCreator)
                   .SingleOrDefault();
            }
            else
            {
                couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id)
                    .Include(c => c.UserCreator)
                    .SingleOrDefault();
            }

            return couponCreator;
        }

        public CouponCreator GetCouponCreatorByIdAndUserCreator(int id, string userCreator, bool withCoupons = false)
        {
            CouponCreator couponCreator = null;

            if (withCoupons)
            {
                 couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator)
                    .Include(c => c.Coupons)
                    .Include(c => c.UserCreator)
                    .SingleOrDefault();
            }
            else
            {
                couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator)
                    .Include(c => c.UserCreator)
                    .SingleOrDefault();
            }

            return couponCreator;
        }

        public CouponCreator RemoveCouponCreator(int id, string userCreator)
        {
            var couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator).SingleOrDefault();

            if (couponCreator == null)
            {
                throw new LogicException("Выбранный купон не найден или уже удален");
            }

            _dataContext.CouponCreators.Remove(couponCreator);
            _dataContext.SaveChanges();

            return couponCreator;
        }

        public CouponCreator ChangeCouponCreator(int id, double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreator)
        {
            if (!CheckLocationProperties(targetX, targetY, radius))
            {
                throw new LogicException("Необходимо ввести все параметры желаемой области");
            }

            var coupon = GetCouponCreatorByIdAndUserCreator(id, userCreator);

            if (coupon == null)
            {
                throw new LogicException("Не удалось найти выбранное предложение");
            }

            coupon.TargetX = targetX;
            coupon.TargetY = targetY;
            coupon.Radius = radius;
            coupon.EndOfCoupon = endOfCoupon;
            coupon.Description = description;

            _dataContext.SaveChanges();

            return coupon;
        }

        public CouponCreator AddCouponCreator(double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreatorName)
        {
            if (!CheckLocationProperties(targetX, targetY, radius))
            {
                throw new LogicException("Необходимо ввести все параметры желаемой области");
            }

            var userCreator = _dataContext.Users.SingleOrDefault(u => u.Username == userCreatorName);

            if (userCreator == null)
            {
                throw new LogicException("У данного пользователя нет прав для создания предложения");
            }

            var couponCreator = new CouponCreator(targetX, targetY, radius, endOfCoupon, description, userCreator);

            _dataContext.CouponCreators.Add(couponCreator);
            _dataContext.SaveChanges();

            return couponCreator;
        }

        public List<CouponCreator> GetCouponCreatorsByFirstAndLastIndex(int startId, int endId)
        {
            return _dataContext.CouponCreators.Where(c => c.Id > startId && c.Id < endId).ToList();
        }

        public bool CheckLocationProperties(double? targetX, double? targetY, double? radius)
        {
            if (targetX == null && targetY == null && radius == null )
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



        
    }
}
