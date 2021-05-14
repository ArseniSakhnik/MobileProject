using MobileProjectSamsung.Authenticate.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Authenticate.Helpers
{
    public static class ExtensionMethods
    {
        public static User WithoutPassword(this User user)
        {
            if (user == null) return null;

            user.Password = null;
            return user;
        }
    }
}
