using MobileProjectSamsung.Application.Data;
using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;

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

        public CouponCreator GetCouponCreatorByIdAndUserCreator(int id, User userCreator, bool withCoupons = false)
        {
            CouponCreator couponCreator = null;

            if (withCoupons)
            {
                 couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator.Username)
                    .Include(c => c.Coupons)
                    .Include(c => c.UserCreator)
                    .SingleOrDefault();
            }
            else
            {
                couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator.Username)
                    .Include(c => c.UserCreator)
                    .SingleOrDefault();
            }

            return couponCreator;
        }

        public CouponCreator RemoveCouponCreator(int id, User userCreator)
        {
            var couponCreator = _dataContext.CouponCreators.Where(c => c.Id == id && c.UserCreator.Username == userCreator.Username).SingleOrDefault();

            if (couponCreator == null)
            {
                return null;
            }

            _dataContext.CouponCreators.Remove(couponCreator);
            _dataContext.SaveChanges();

            return couponCreator;
        }

        public CouponCreator ChangeCouponCreator(int id, double targetX, double targetY, double radius, DateTime endOfCoupon, string description, User userCreator)
        {
            var coupon = GetCouponCreatorByIdAndUserCreator(id, userCreator);

            if (coupon == null)
            {
                return null;
            }

            coupon.TargetX = targetX;
            coupon.TargetY = targetY;
            coupon.Radius = radius;
            coupon.EndOfCoupon = endOfCoupon;
            coupon.Description = description;

            _dataContext.SaveChanges();

            return coupon;
        }

        public CouponCreator AddCouponCreator(int id, double targetX, double targetY, double radius, DateTime endOfCoupon, string description, User userCreator)
        {
            var couponCreator = GetCouponCreatorByIdAndUserCreator(id, userCreator);

            if (couponCreator == null)
            {
                return null;
            }

            couponCreator = new CouponCreator(targetX, targetY, radius, endOfCoupon, description, userCreator);

            _dataContext.CouponCreators.Add(couponCreator);
            _dataContext.SaveChanges();

            return couponCreator;
        }

        public List<CouponCreator> GetCouponCreatorsByFirstAndLastIndex(int startId, int endId)
        {
            return _dataContext.CouponCreators.Where(c => c.Id > startId && c.Id < endId).ToList();
        }



        
    }
}
