using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Models
{
    public class GetCouponCreatorListRequest
    {
        [Required]
        public int StartId { get; set; }
        [Required]
        public int EndId { get; set; }
    }
}
