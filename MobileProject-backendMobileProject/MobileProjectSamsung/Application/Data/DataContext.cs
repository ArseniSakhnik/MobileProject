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
                    Username = "counterparty",
                    FirstName = "Counterparty",
                    LastName = "counterparty",
                    Role = Role.Counterparty,
                    Password = BC.HashPassword("counterparty")
                },
                new
                {
                    Username = "test",
                    FirstName = "test",
                    LastName = "test",
                    Role = Role.Counterparty,
                    Password = BC.HashPassword("test")
                }
                );
        }
    }
}
