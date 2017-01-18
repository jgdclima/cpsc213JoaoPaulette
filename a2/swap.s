.pos 0x100
	ld $t, r1		#t = r1
	ld $array, r2		#array[0] = r2
	ld (r2), r1		#t = array[0]
	
	ld $0x2004, r3		#array[1] = r2
	ld (r3), r4		#gets array[1], sets to r3
	st r4, (r2)
	st r1, (r3)		#stores array[0] in array[1]
.pos 0x1000
t: 	.long 0xffffffff 	#t=0

.pos 0x2000
array:		.long 0x00000002 	#array[0] = 2
		.long 0x00000004	#array[1] = 4

