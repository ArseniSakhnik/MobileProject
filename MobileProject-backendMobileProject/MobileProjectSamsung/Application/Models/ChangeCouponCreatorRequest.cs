using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Models
{
    public class ChangeCouponCreatorRequest
    {
        public int Id { get; set; }
        public double? TargetX { get; set; }
        public double? TargetY { get; set; }
        public double? Radius { get; set; }
        public DateTime? EndOfCoupon { get; set; }
        public string Description { get; set; }
    }
}
