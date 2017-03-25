def fac(x : Int) : Int = {
  if(x == 0)
  	return 0

  if(x == 1)
    return 1

  return fac(x - 1) + fac(x - 2)
}

val value = fac(10)

println(value)
