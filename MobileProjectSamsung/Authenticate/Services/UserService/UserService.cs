﻿using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using MobileProjectSamsung.Application.Entities;
using MobileProjectSamsung.Application.Data;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using BC = BCrypt.Net.BCrypt;
using System.Threading.Tasks;
using BCrypt.Net;
using MobileProjectSamsung.Application.Exceptions;
using Microsoft.EntityFrameworkCore;

namespace MobileProjectSamsung.Application.Services.UserService
{
    public class UserService : IUserService
    {
        private readonly AppSettings _appSettings;
        private readonly DataContext _dataContext;

        public UserService(IOptions<AppSettings> appSettings, DataContext dataContext)
        {
            _appSettings = appSettings.Value;
            _dataContext = dataContext;
        }

        public User Authenticate(string username, string password)
        {

            var user = _dataContext.Users.SingleOrDefault(u => u.Username == username);

            if (user == null || !BC.Verify(password, user.Password))
            {
                throw new LogicException("Неправильный логин или пароль");
            }
            else
            {
                GenerateToken(user);
                return user.WithoutPassword();
            }

        }

        public User Register(string username, string password, string firstName, string lastName, string role)
        {

            var user = _dataContext.Users.SingleOrDefault(u => u.Username == username);

            if (user != null)
            {
                throw new LogicException("Пользователь с указанным именем уже существует");
            }

            user = new User(username, BC.HashPassword(password), firstName, lastName, role);

            _dataContext.Users.Add(user);
            _dataContext.SaveChanges();

            GenerateToken(user);
            return user.WithoutPassword();
        }

        public User GetUserByUsername(string username, bool withCupons = false)
        {
            if (withCupons)
            {
                return _dataContext.Users.Where(u => u.Username == username).Include(u => u.Coupons).SingleOrDefault();
            }
            else
            {
                return _dataContext.Users.SingleOrDefault(u => u.Username == username);
            }
        }

        public void GenerateToken(User user)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(_appSettings.Secret);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.Name, user.Username),
                    new Claim(ClaimTypes.Role, user.Role)
                }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            user.Token = tokenHandler.WriteToken(token);
        }
    }
}