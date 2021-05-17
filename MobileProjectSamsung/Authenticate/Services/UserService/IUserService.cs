using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Services.UserService
{
    public interface IUserService
    {
        User Authenticate(string username, string password);
        User Register(string username, string password, string firstName, string lastName, string role);
        User GetUserByUsername(string username, bool withCoupons = false);
    }
}
