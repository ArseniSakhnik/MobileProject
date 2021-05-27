using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Services.UserService
{
    public interface IUserService
    {
        public Task<User> AuthenticateAsync(string username, string password);
        public Task<User> RegisterAsync(string username, string password, string firstName, string lastName, string role);
        public Task<User> GetUserByUsernameAsync(string username, bool withCoupons = false);
    }
}
