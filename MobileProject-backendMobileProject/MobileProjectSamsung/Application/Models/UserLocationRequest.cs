using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Models
{
    public class UserLocationRequest
    {
        public double? TargetX { get; set; } = null;
        public double? TargetY { get; set; } = null;
    }
}
