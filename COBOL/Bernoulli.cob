IDENTIFICATION DIVISION.
PROGRAM-ID. BERNOULLI.

DATA DIVISION.
WORKING-STORAGE SECTION.
	77 bzero pic 9V9999.
	77 bm pic 9V9999.
	77 y pic 9V9999.
	77 m pic 999.
	77 k pic 999.
	77 r pic 99V9999.
	77 i pic 999.
	77 testet pic 9V99.



PROCEDURE DIVISION.
	move 1 to bzero
	move 1 to m

	COMPUTE testet = - 1 / 2
		
	move 0 to bm
	perform until m greater than 20
		move 0 to y
		move 0 to k
		perform until k greater than (m - 1)
			move 1 to r
			move 1 to i
			perform until i greater than k
				COMPUTE r = r * (m + 2 - i) / i
				add 1 to i
			end-perform

			*> förmodligen bzeros som är fel????
			COMPUTE y = bm - (r * bzero)
			move bm to bzero
			move y to bm
			add 1 to k

		end-perform
		COMPUTE y = y / (m + 1)
		add 1 to m
		DISPLAY y
	end-perform



STOP RUN.
