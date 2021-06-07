using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MobileProjectSamsung.Application.Exceptions;
using MobileProjectSamsung.Application.Models;
using MobileProjectSamsung.Application.Services.CouponCreatorService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Conrtollers
{
    
    [ApiController]
    [Authorize]
    [Route("[controller]")]
    public class CouponCreatorController : ControllerBase
    {
        private readonly ICouponCreatorService _couponCreatorService;

        public CouponCreatorController(ICouponCreatorService couponCreatorService)
        {
            _couponCreatorService = couponCreatorService;
        }

        [HttpPost("getCouponListByCount/{startId}/{count}")]
        [Authorize]
        public async Task<IActionResult> GetCouponCreatorsByFirstIndexAndCount(int startId, int count, [FromBody] UserLocationRequest model) 
        {
            try
            {
                var coupons = await _couponCreatorService.GetCouponCreatorsByFirstIndexAndCountAsync(startId, count, model.TargetX, model.TargetY);
                return Ok(coupons);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpPost("getCouponCreatorListByIdAndSearch/{count}")]
        [Authorize]
        public async Task<IActionResult> GetCouponCreatorBySearchAndFirstIdAndCount(int count, [FromQuery(Name = "search")] string search, [FromBody] UserLocationRequest model) 
        {
            try
            {
                var coupons = await _couponCreatorService.GetCouponCreatorsBySearchAndFirsIdAndCountAsync(count, search, model.TargetX, model.TargetY);
                return Ok(coupons);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpPost("addCouponCreator")]
        [Authorize(Roles = Entities.Role.Counterparty)]
        public async Task<IActionResult> AddCouponCreator([FromBody] AddCouponCreatorRequest model)
        {
            try
            {
                var userName = User.FindFirstValue(ClaimTypes.Name);
                var couponCreator = await _couponCreatorService.AddCouponCreatorAsync(model.TargetX, model.TargetY, model.Radius, model.EndOfCoupon, model.Description, userName);
                return Ok(couponCreator);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpDelete("RemoveCouponCreator/{id}")]
        [Authorize(Roles = Entities.Role.Counterparty)]
        public async Task<IActionResult> RemoveCouponCreator(int id)
        {
            try
            {
                var userName = User.FindFirstValue(ClaimTypes.Name);
                var couponCreator = await _couponCreatorService.RemoveCouponCreatorAsync(id, userName);
                return Ok(couponCreator);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
            
        }

        [HttpPut("changeCouponCreator/{id}")]
        [Authorize(Roles = Entities.Role.Counterparty)]
        public async Task<IActionResult> ChangeCouponCreator(int id, [FromBody] ChangeCouponCreatorRequest model)
        {
            try
            {
                var userName = User.FindFirstValue(ClaimTypes.Name);
                var couponCreator = await _couponCreatorService.ChangeCouponCreatorAsync(id, model.TargetX, model.TargetY, model.Radius, model.EndOfCoupon, model.Description, userName);
                return Ok(couponCreator);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}
