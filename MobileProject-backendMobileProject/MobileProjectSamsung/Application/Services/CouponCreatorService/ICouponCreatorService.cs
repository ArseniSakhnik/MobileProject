using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Services.CouponCreatorService
{
    public interface ICouponCreatorService
    {
        public Task<CouponCreator> GetCouponCreatorByIdAsync(int id, bool withCoupons = false);
        public Task<CouponCreator> GetCouponCreatorByIdAndUserCreatorAsync(int id, string userCreator, bool withCoupons = false);
        public Task<CouponCreator> RemoveCouponCreatorAsync(int id, string userCreator);
        public Task<CouponCreator> ChangeCouponCreatorAsync(int id, double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreator);
        public Task<CouponCreator> AddCouponCreatorAsync(double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, string userCreatorName);
        public Task<List<CouponCreator>> GetCouponCreatorsByFirstIndexAndCountAsync(int startId, int count, double? xPosition, double? yPosition);
        public bool CheckLocationProperties(double? targetX, double? targetY, double? radius);
        public Task<List<CouponCreator>> GetCouponCreatorsBySearchAndFirsIdAndCountAsync(int count, string searchName, double? xPosition, double? yPosition);
    }
}
