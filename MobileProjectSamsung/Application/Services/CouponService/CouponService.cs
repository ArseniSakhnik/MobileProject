using MobileProjectSamsung.Application.Data;
using MobileProjectSamsung.Application.Entities;
using MobileProjectSamsung.Application.Exceptions;
using MobileProjectSamsung.Application.Services.CouponCreatorService;
using MobileProjectSamsung.Application.Services.UserService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

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
            catch
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
                throw new LogicException("Пользователь уже узял этот купон");
            }

            if (couponCreator.EndOfCoupon != null && couponCreator.EndOfCoupon > DateTime.UtcNow)
            {
                throw new LogicException("Время действия купона истекло.");
            }

            if (userLocationX != null && userLocationY != null && CheckLocationIsNeeded(couponCreator))
            {
                if (CheckLocationConditionsOfUser(userLocationX, userLocationY, couponCreator.TargetX, couponCreator.TargetY, couponCreator.Radius))
                {
                    coupon = new Coupon(user, couponCreator);
                }
                else
                {
                    throw new LogicException("Неподходящее местоположение для пользователя");
                }
            }
            else if ((userLocationX == null || userLocationY == null) && CheckLocationIsNeeded(couponCreator))
            {
                throw new LogicException("Необходимо отправить локацию пользователя для этого предложения");
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
            var user = _userService.GetUserByUsername(username);

            if (user == null)
            {
                throw new LogicException("Пользователь не найден");
            }

            var coupon = _dataContext.Coupons.SingleOrDefault(c => c.Id == couponId);

            if (coupon == null)
            {
                throw new LogicException("Купон уже удален или не был выбран");
            }

            user.Coupons.Remove(coupon);

            _dataContext.SaveChanges();

            return coupon;
        }

        private bool CheckLocationConditionsOfUser(double? userX, double? userY, double? targetX, double? targetY, double? targetRadius)
        {
            return Math.Pow(((double)userX - (double)targetX), 2) + Math.Pow(((double)userY - (double)targetY), 2) <= Math.Pow((double)targetRadius, 2);
        }

        private bool CheckLocationIsNeeded(CouponCreator couponCreator)
        {
            return _couponCreatorService.CheckLocationProperties(couponCreator.TargetX, couponCreator.TargetY, couponCreator.Radius);
        }


    }
}
