using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Services.CouponCreatorService
{
    public interface ICouponCreatorService
    {
        public CouponCreator GetCouponCreatorById(int id, bool withCoupons = false);
        public CouponCreator GetCouponCreatorByIdAndUserCreator(int id, User userCreator, bool withCoupons = false);
        public CouponCreator RemoveCouponCreator(int id, User userCreator);
        public CouponCreator ChangeCouponCreator(int id, double targetX, double targetY, double radius, DateTime endOfCoupon, string description, User userCreator);
        public CouponCreator AddCouponCreator(int id, double targetX, double targetY, double radius, DateTime endOfCoupon, string description, User userCreator);
        public List<CouponCreator> GetCouponCreatorsByFirstAndLastIndex(int startId, int endId);

    }
}
