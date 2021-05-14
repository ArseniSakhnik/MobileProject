using MobileProjectSamsung.Authenticate.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Authenticate.Services.UserService
{
    public interface IUserService
    {
        User Authenticate(string username, string password);
    }
}
