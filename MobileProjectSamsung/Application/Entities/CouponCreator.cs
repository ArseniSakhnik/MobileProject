using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Entities
{
    public class CouponCreator
    {
        [Key]
        public int Id { get; set; }
        public double? TargetX { get; set; } = null;
        public double? TargetY { get; set; } = null;
        public double? Radius { get; set; } = null;
        public DateTime? EndOfCoupon { get; set; } = null;
        public bool IsActive { get => EndOfCoupon > DateTime.Now || EndOfCoupon == null; set => IsActive = value; }
        public List<Coupon> Coupons { get; set; }
        [MinLength(10), MaxLength(100)]
        public string Description { get; set; }
        [Required]
        public User UserCreator { get; set; }
        public int UserCreatorId { get; set; }

        public CouponCreator()
        {
            Coupons = new List<Coupon>();
        }

        public CouponCreator(double? targetX, double? targetY, double? radius, DateTime? endOfCoupon, string description, User userCreator)
        {
            TargetX = targetX;
            TargetY = targetY;
            Radius = radius;
            EndOfCoupon = endOfCoupon;
            Description = description;
            UserCreator = userCreator;
        }
    }
}
