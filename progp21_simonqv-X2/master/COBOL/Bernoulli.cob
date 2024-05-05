IDENTIFICATION DIVISION.
PROGRAM-ID. BERNOULLI.

DATA DIVISION.
WORKING-STORAGE SECTION.
	77 bm pic S9(10)V9(15).
	77 y pic S9(10)V9(15).
	77 m pic 9(2).
	77 k pic 9(2).
	77 r pic S9(10)V9(15).
	77 i pic 9(2).
	77 disp pic S9(2)V9(7).
	01 WS-TABLE.
		05 B pic S9(10)V9(15) OCCURS 20 TIMES.



PROCEDURE DIVISION.
	move 1 to B (1)
	move 1 to m
	move B (1) to disp
	DISPLAY disp
		
	
	perform until m greater than 19
		move 0 to y
		move 0 to bm
		move 0 to k
		perform until k greater than (m + 1)
			move 1 to r
			
			move 1 to i
			perform until i greater than k
				COMPUTE r = r * (m + 2 - i) / i
				add 1 to i
			end-perform
					
			
			COMPUTE bm = bm - (r * B (k + 1))
			
			
			add 1 to k

		end-perform
		
		COMPUTE y = bm / (m + 1)
		
		move y to B (m + 1)
		add 1 to m
		move y to disp
		DISPLAY disp 

	end-perform



STOP RUN.
