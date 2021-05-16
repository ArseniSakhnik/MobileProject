using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Application.Entities
{
    public class User
    {
        [Key]
        [MinLength(2), MaxLength(20)]
        public string Username { get; set; }
        [Required, MinLength(2), MaxLength(20)]
        public string FirstName { get; set; }
        [Required, MinLength(2), MaxLength(20)]
        public string LastName { get; set; }
        [Required]
        public string Role { get; set; }
        public string Token { get; set; }
        [Required, MinLength(4)]
        public string Password { get; set; }
        public List<Coupon> Coupons { get; set; }

        public User()
        {
            Coupons = new List<Coupon>();
        }

        public User(string username, string password, string firstName, string lastName, string role)
        {
            Username = username;
            FirstName = firstName;
            LastName = lastName;
            Role = role;
            Password = password;
            Coupons = new List<Coupon>();
        }
    }
}
