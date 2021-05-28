using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Entities
{
    public class Coupon
    {
        [Key]
        public int Id { get; set; }
        [JsonIgnore]
        public User CouponUser { get; set; }
        public string CouponUserUsername { get; set; }
        [Required, JsonIgnore]
        public CouponCreator CouponCreator { get; set; }
        public int CouponCreatorId { get; set; }
        [JsonIgnore]
        public bool WasActivated { get; set; } = false;
        public bool IsActive { get => !WasActivated && CouponCreator.IsActive; }
        public string Description { get => CouponCreator.Description; }
        public DateTime? EndOfCoupon { get => CouponCreator.EndOfCoupon; }

        public Coupon() { }

        public Coupon(User user, CouponCreator couponCreator)
        {
            CouponUser = user;
            CouponCreator = couponCreator;
        }
    }
}
