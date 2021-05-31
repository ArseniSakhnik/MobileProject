using Microsoft.EntityFrameworkCore;
using MobileProjectSamsung.Application.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BC = BCrypt.Net.BCrypt;

namespace MobileProjectSamsung.Application.Data
{
    public class DataContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<CouponCreator> CouponCreators { get; set; }
        public DbSet<Coupon> Coupons { get; set; }


        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
            Database.EnsureCreated();
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<CouponCreator>().Ignore(c => c.IsActive);
            modelBuilder.Entity<Coupon>().Ignore(c => c.IsActive);

            modelBuilder.Entity<User>().HasData(
                new
                {
                    Username = "admin",
                    FirstName = "Admin",
                    LastName = "Admin",
                    Role = Role.Admin,
                    Password = BC.HashPassword("admin")
                },
                new
                {
                    Username = "user",
                    FirstName = "User",
                    LastName = "User",
                    Role = Role.User,
                    Password = BC.HashPassword("user")
                },
                new
                {
                    Username = "сounterparty",
                    FirstName = "Counterparty",
                    LastName = "Counterparty",
                    Role = Role.Counterparty,
                    Password = BC.HashPassword("сounterparty")
                });

            modelBuilder.Entity<CouponCreator>().HasData(
                new
                {
                    Id = 1,
                    UserCreatorUsername = "counterparty",
                    Description = "Скидка 5% на все в суши-баре \"SUSHI\""
                },
                new
                {
                    Id = 2,
                    UserCreatorUsername = "counterparty",
                    Description = "Скидка 10% на шаверму в \"Мангал-хаус\"",
                    EndOfCoupon = DateTime.UtcNow.AddDays(7),
                },
                new
                {
                    Id = 3,
                    UserCreatorUsername = "counterparty",
                    Description = "Скидка 15% на шашлы в \"Шашлычной\"",
                    TargetX = 26.6,
                    TargetY = 150.5,
                    Radius = 180,
                    EndOfCoupon = DateTime.UtcNow.AddDays(5)
                }
                );

            //modelBuilder.Entity<Coupon>().HasData(
            //    new 
            //    {
            //        Id = 1, 
            //        CouponUserUsername = "user",
            //        CouponCreatorId = 1,
            //        WasActivated = false
            //    },
            //    new
            //    {
            //        Id = 2, 
            //        CouponUserUsername = "user",
            //        CouponCreatorId = 2,
            //        WasActivated = false
            //    }
            //    new
            //    {
            //        Id = 3,
            //        CouponUserUsername = "user",
            //        CouponCreatorId = 3,
            //        WasActivated = false,
            //    }
            //    );



        }
    }
}
