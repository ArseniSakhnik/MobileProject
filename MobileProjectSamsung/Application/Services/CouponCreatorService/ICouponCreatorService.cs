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
        public CouponCreator GetCouponCreatorByIdAndUserCreator(int id, string userCreator, bool withCoupons = false);
        public CouponCreator RemoveCouponCreator(int id, string userCreator);
        public CouponCreator ChangeCouponCreator(int id, double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreator);
        public CouponCreator AddCouponCreator(double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreatorName);
        public List<CouponCreator> GetCouponCreatorsByFirstAndLastIndex(int startId, int endId);
        public bool CheckLocationProperties(double? targetX, double? targetY, double? radius);
        List<CouponCreator> GetCouponCreatorsBySearchAndFirstAndLastId(int startId, int lastId, string searchName);
    }
}
