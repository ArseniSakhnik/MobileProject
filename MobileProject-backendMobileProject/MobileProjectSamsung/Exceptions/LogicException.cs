using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MobileProjectSamsung.Exceptions
{
    public class LogicException : Exception
    {
        public LogicException(string message) : base(message) { }
    }
}
