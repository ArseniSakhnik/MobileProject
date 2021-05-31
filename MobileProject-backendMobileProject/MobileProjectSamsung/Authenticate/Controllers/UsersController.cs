using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MobileProjectSamsung.Application.Entities;
using MobileProjectSamsung.Application.Exceptions;
using MobileProjectSamsung.Application.Models;
using MobileProjectSamsung.Application.Services.UserService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Controllers
{
    [Authorize]
    [ApiController]
    [Route("[controller]")]
    public class UsersController : ControllerBase
    {
        private IUserService _userService;
        private readonly IHttpContextAccessor _httpContextAccessor;

        public UsersController(IUserService userService, IHttpContextAccessor httpContextAccessor)
        {
            _userService = userService;
            _httpContextAccessor = httpContextAccessor;
        }

        [AllowAnonymous]
        [HttpPost("authenticate")]  
        public async Task<IActionResult> Authenticate([FromBody] AuthenticateModel model)
        {
            try
            {
                var user = await _userService.AuthenticateAsync(model.Username, model.Password);
                return Ok(user);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [AllowAnonymous]
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterModel model)
        {
            try
            {
                var user = await _userService.RegisterAsync(model.Username, model.Password, model.FirstName, model.LastName, model.Role);
                return Ok(user);
            }
            catch (LogicException ex)
            {
                return BadRequest(new { message = ex.Message }); 
            }
        }

        [Authorize(Roles = Role.Admin)]
        [HttpGet("testadmin")]
        public IActionResult TestAdmin()
        {
            return Ok(new { message = "you are admin" });
        }

        [Authorize(Roles = Role.User)]
        [HttpGet("testuser")]
        public IActionResult TestUser()
        {
            return Ok(new { message = "you are user" });
        }

        [AllowAnonymous]
        [HttpGet("test")]
        public IActionResult Test()
        {
            return Ok(new { message = "test" });
        }

        [Authorize]
        [HttpGet("testusername")]
        public IActionResult TestUserName()
        {
            return Ok(new { message = $"U are {User.FindFirstValue(ClaimTypes.Name)} with role {User.FindFirstValue(ClaimTypes.Role)}" });
        }

    }
}
