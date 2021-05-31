using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Services.CouponService
{
    public interface ICouponService
    {
        public Task<Coupon> AddCouponToUserWithExceptionsAsync(string username, int couponCreatorId, double? userLocationX = null, double? userLocationY = null);
        public Task<Coupon> RemoveCouponFromUserAsync(string username, int couponId);
        public Task<List<Coupon>> GetUserCouponsAsync(string username);
    }
}
