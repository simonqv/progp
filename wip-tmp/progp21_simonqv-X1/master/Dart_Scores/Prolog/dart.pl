[kattio].

a(1). a(2). a(3).
x(1). x(2). x(3). x(4). x(5). x(6). x(7). x(8). x(9). x(10). x(11). x(12). x(13). x(14). x(15). x(16). x(17). x(18). x(19). x(20).

main :-
	repeat,
	read_int(X),
	(X == end_of_file ;
		solve(X),
		fail
	).

solve(S) :-
    x(X), x(Y), x(Z),
    a(A), a(B), a(C),
    test(S, X, Y, Z, A, B, C), !;
    write("impossible"), nl, !.

test(S, X, Y, Z, A, B, C) :-
    S is A*X + B*Y + C*Z,
    printer(A), write(" "), write(X), nl,
    printer(B), write(" "), write(Y), nl,
    printer(C), write(" "), write(Z), nl. 

printer(1) :- write("single ").
printer(2) :- write("double ").
printer(3) :- write("triple ").