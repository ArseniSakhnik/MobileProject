using MobileProjectSamsung.Application.Data;
using MobileProjectSamsung.Application.Entities;
using MobileProjectSamsung.Application.Exceptions;
using MobileProjectSamsung.Application.Services.CouponCreatorService;
using MobileProjectSamsung.Application.Services.UserService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace MobileProjectSamsung.Application.Services.CouponService
{
    public class CouponService : ICouponService
    {
        private readonly DataContext _dataContext;
        private readonly IUserService _userService;
        private readonly ICouponCreatorService _couponCreatorService;

        public CouponService(DataContext dataContext, IUserService userService, ICouponCreatorService couponCreatorService)
        {
            _dataContext = dataContext;
            _userService = userService;
            _couponCreatorService = couponCreatorService;
        }

        public List<Coupon> GetUserCoupons(string username)
        {
            try
            {
                var user = _userService.GetUserByUsername(username, withCoupons: true);
                if (user.Coupons.Count() == 0)
                {
                    throw new LogicException("У пользователя ещё нет купонов.");
                }
                return user.Coupons;
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public Coupon AddCouponToUserWithExceptions(string username, int couponCreatorId, double? userLocationX = null, double? userLocationY = null)
        {
            try
            {
                return AddCouponToUser(username, couponCreatorId, userLocationX, userLocationY);
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public Coupon AddCouponToUser(string username, int couponCreatorId, double? userLocationX = null, double? userLocationY = null)
        {
            var user = _userService.GetUserByUsername(username, withCoupons: true);
            var couponCreator = _couponCreatorService.GetCouponCreatorById(couponCreatorId, withCoupons: true);
            var coupon = user.Coupons.Where(c => c.CouponCreatorId == couponCreatorId).SingleOrDefault();

            if (coupon != null)
            {
                throw new LogicException("Пользователь уже узял этот купон.");
            }

            if (couponCreator == null)
            {
                throw new LogicException("Выбранное предложение удалено.");
            }

            if (couponCreator.EndOfCoupon != null)
            {
                if (couponCreator.EndOfCoupon < DateTime.UtcNow)
                {
                    throw new LogicException("Время действия купона истекло");
                }
            }

            if (couponCreator.TargetX != null)
            {
                if (userLocationX == null)
                {
                    throw new LogicException("Для использования этого купона необходима геолокация пользователя");
                }


                if (CheckLocationConditionsOfUser(userLocationX,
                    userLocationY, couponCreator.TargetX, couponCreator.TargetY, couponCreator.Radius))
                {
                    coupon = new Coupon(user, couponCreator);
                }
                else
                {
                    throw new LogicException("Неподходящая позиция для пользователя");
                }
            }
            else
            {
                coupon = new Coupon(user, couponCreator);
            }

            _dataContext.Coupons.Add(coupon);
            _dataContext.SaveChanges();

            return coupon;
        }

        public Coupon RemoveCouponFromUser(string username, int couponId)
        {
            var user = _userService.GetUserByUsername(username, withCoupons:true);

            if (user == null)
            {
                throw new LogicException("Пользователь не найден");
            }

            var coupon = _dataContext.Coupons.Where(c => c.Id == couponId).Include(c => c.CouponCreator).SingleOrDefault();

            if (coupon == null)
            {
                throw new LogicException("Купон уже удален или не был выбран");
            }


            _dataContext.Coupons.Remove(coupon);

            _dataContext.SaveChanges();

            return coupon;
        }

        private bool CheckLocationConditionsOfUser(double? userX, double? userY, double? targetX, double? targetY, double? targetRadius)
        {
            return Math.Pow(((double)userX - (double)targetX), 2) + Math.Pow(((double)userY - (double)targetY), 2) <= Math.Pow((double)targetRadius, 2);
        }


    }
}
