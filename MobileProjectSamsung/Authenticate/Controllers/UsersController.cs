using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MobileProjectSamsung.Authenticate.Entities;
using MobileProjectSamsung.Authenticate.Models;
using MobileProjectSamsung.Authenticate.Services.UserService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Authenticate.Controllers
{
    [Authorize]
    [ApiController]
    [Route("[controller]")]
    public class UsersController : ControllerBase
    {
        private IUserService _userService;

        public UsersController(IUserService userService)
        {
            _userService = userService;
        }

        [AllowAnonymous]
        [HttpPost("authenticate")]
        public IActionResult Authenticate([FromBody] AuthenticateModel model)
        {
            var user = _userService.Authenticate(model.Username, model.Password);

            if (user == null)
            {
                return BadRequest(new { message = "Нерпавильный логин или праоль" });
            }

            return Ok(user);
        }

        [AllowAnonymous]
        [HttpPost("register")]
        public IActionResult Register([FromBody] RegisterModel model)
        {
            var user = _userService.Register(model.Username, model.Password, model.FirstName, model.LastName, model.Role);

            if (user == null)
            {
                return BadRequest(new { message = "Пользователь с указанным именем уже зарегестрирован" });
            }

            return Ok(user);
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
            //return Ok(new { message = "test" });
            return BadRequest("error test");
        }

    }
}
