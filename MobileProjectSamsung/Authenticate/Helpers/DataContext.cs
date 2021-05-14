using Microsoft.EntityFrameworkCore;
using MobileProjectSamsung.Authenticate.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Authenticate.Helpers
{
    public class DataContext : DbContext
    {
        public DbSet<User> Users { get; set; }


        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
            Database.EnsureCreated();
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().HasData(
                new { Username = "admin", FirstName = "Admin", LastName = "Admin", Role = Role.Admin, Password = "admin"},
                new { Username = "user", FirstName = "User", LastName = "User", Role = Role.User, Password = "user" }
                );
        }
    }
}
