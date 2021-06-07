using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MobileProjectSamsung.Application.Exceptions;
using MobileProjectSamsung.Application.Models;
using MobileProjectSamsung.Application.Services.CouponService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Conrtollers
{
    [Authorize]
    [ApiController]
    [Route("[controller]")]
    public class CouponController : ControllerBase
    {
        private readonly ICouponService _couponService;

        public CouponController(ICouponService couponService)
        {
            _couponService = couponService;
        }

        [HttpGet("getUserCoupons")]
        public async Task<IActionResult> GetUserCoupons()
        {
            try
            {
                var username = User.FindFirstValue(ClaimTypes.Name);
                var coupons = await _couponService.GetUserCouponsAsync(username);
                return Ok(coupons);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpPost("addCouponToUser/{couponId}")]
        public async Task<IActionResult> AddCouponToUser(int couponId, [FromBody] UserLocationRequest model)
        {
            try
            {
                var username = User.FindFirstValue(ClaimTypes.Name);
                var coupon = await _couponService.AddCouponToUserWithExceptionsAsync(username, couponId, model.TargetX, model.TargetY);
                return Ok(coupon);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpDelete("removeCouponFromUser/{couponId}")]
        public async Task<IActionResult> RemoveCouponFromUser(int couponId)
        {
            try
            {
                var username = User.FindFirstValue(ClaimTypes.Name);
                var coupon = await _couponService.RemoveCouponFromUserAsync(username, couponId);
                return Ok(coupon);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}
