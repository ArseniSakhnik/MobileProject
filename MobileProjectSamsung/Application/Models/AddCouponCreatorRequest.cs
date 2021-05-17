using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Models
{
    public class AddCouponCreatorRequest
    {
        public double? TargetX { get; set; } = null;
        public double? TargetY { get; set; } = null;
        public double? Radius { get; set; } = null;
        public DateTime? EndOfCoupon { get; set; } = null;
        public string Description { get; set; }
    }
}
