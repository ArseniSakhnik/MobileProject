using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Services.CouponService
{
    public interface ICouponService
    {
        public Coupon AddCouponToUserWithExceptions(string username, int couponCreatorId, double? userLocationX = null, double? userLocationY = null);
        public Coupon RemoveCouponFromUser(string username, int couponId);
        public List<Coupon> GetUserCoupons(string username);
    }
}
