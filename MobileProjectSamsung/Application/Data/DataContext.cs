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
                    Password = BC.HashPassword("Counterparty")
                }
                );

            modelBuilder.Entity<CouponCreator>().HasData(
                new
                {
                    Id = 1,
                    TargetX = 64.5374,
                    TargetY = 39.7788,
                    Radius = 68.12,
                    EndOfCoupon = DateTime.Now.AddDays(7),
                    Description = "Это тестоый купон на скидку 5%"
                },
                new
                {
                    Id = 2,
                    TargetX = 97.3423,
                    TargetY = 121.213,
                    Radius = 80.67,
                    EndOfCoupon = DateTime.Now.AddDays(3),
                    Description = "Это ещё один тестовый купон на скидку 10%"
                }
                );
        }
    }
}
