double XGlobal(int celln,double x)
{/*ALCODESTART::1779873455488*/
return ( ( celln % Width) * CellWidth ) + x;
/*ALCODEEND*/}

double YGlobal(int celln,double x)
{/*ALCODESTART::1779873560795*/
return ( ( int) (celln/ Width) * CellWidth ) + x;
/*ALCODEEND*/}

