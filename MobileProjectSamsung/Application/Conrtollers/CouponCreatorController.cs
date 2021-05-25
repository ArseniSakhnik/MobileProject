﻿using Microsoft.AspNetCore.Authorization;
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

        [HttpGet("getCouponList/{startId}/{endId}")]
        [Authorize]
        public IActionResult GetCouponCreatorListByFirstAndLastId(int startId, int endId)
        {
            var coupons = _couponCreatorService.GetCouponCreatorsByFirstAndLastIndex(startId, endId);

            if (coupons.Count == 0)
            {
                return BadRequest(new { message = "Не удалось получить предложения." });
            }

            return Ok(coupons);
        }

        [HttpPost("addCouponCreator")]
        [Authorize(Roles = Entities.Role.Counterparty)]
        public IActionResult AddCouponCreator([FromBody] AddCouponCreatorRequest model)
        {
            try
            {
                var userName = User.FindFirstValue(ClaimTypes.Name);
                var couponCreator = _couponCreatorService.AddCouponCreator(model.TargetX, model.TargetY, model.Radius, model.EndOfCoupon, model.Description, userName);
                return Ok(couponCreator);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpDelete("RemoveCouponCreator/{id}")]
        [Authorize(Roles = Entities.Role.Counterparty)]
        public IActionResult RemoveCouponCreator(int id)
        {
            try
            {
                var userName = User.FindFirstValue(ClaimTypes.Name);
                var couponCreator = _couponCreatorService.RemoveCouponCreator(id, userName);
                return Ok(couponCreator);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
            
        }

        [HttpPut("changeCouponCreator/{id}")]
        [Authorize(Roles = Entities.Role.Counterparty)]
        public IActionResult ChangeCouponCreator(int id, [FromBody] ChangeCouponCreatorRequest model)
        {
            try
            {
                var userName = User.FindFirstValue(ClaimTypes.Name);
                var couponCreator = _couponCreatorService.ChangeCouponCreator(id, model.TargetX, model.TargetY, model.Radius, model.EndOfCoupon, model.Description, userName);
                return Ok(couponCreator);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}