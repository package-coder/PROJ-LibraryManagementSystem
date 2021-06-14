// Function - input (argument), code, output (return value)

let greetUser = function () {
  console.log('Welcome user!')
}

greetUser()
greetUser()
greetUser()

let square = function (num) {
  let result = num * num
  return result
}

let value = square(3)
let otherValue = square(10)
console.log(value)
console.log(otherValue)

// Challenge Area

// convertFahrenheitToCelsius

// Call a couple of times (32 => 0) (68 -> 20)

// Print the converted values

let fahrenheitToCelsius = function (fahrenheit) {
  let celcius = (fahrenheit - 32) * (5 / 9)
  return celcius
}

console.log(fahrenheitToCelsius(32))
console.log(fahrenheitToCelsius(68))
