using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Authenticate.Entities
{
    public class User
    {
        [Key]
        public string Username { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Role { get; set; }
        public string Token { get; set; }
        public string Password { get; set; }

        public User(string username, string password, string firstName, string lastName, string role)
        {
            Username = username;
            FirstName = firstName;
            LastName = lastName;
            Role = role;
            Password = password;
        }
    }
}
