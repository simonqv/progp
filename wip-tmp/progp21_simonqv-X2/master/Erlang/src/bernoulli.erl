-module(bernoulli).
-compile(export_all).
-import(lists, [append/2,nth/2]).
-import(math, [pow/2]).

main() -> b(20).


b(N) ->
  B = lists:append([], [1.0]),
  Bdone = outer_loop(1, N, B),
  printer(Bdone).


outer_loop(M, N, B) when M == N -> B;
outer_loop(M, N, B) ->
  Bm = (inner_loop(M-1, 0, B, 0.0)) / (M+1),
  Pow = pow(10, -8),
  X = if
    abs(Bm) < Pow -> 0.0;
    abs(Bm) >= Pow -> Bm
  end,
  outer_loop(M+1, N, lists:append(B, [X])).

inner_loop(M, K, [], Bm) when K == M-1 -> Bm;
inner_loop(M,K, [], Bm) when K /= M-1 -> Bm;
inner_loop(M, K, [B|BT], Bm) ->
  inner_loop(M, K+1, BT, (Bm - (binom(M+2, K) * B))).




binom(N, K) -> binom_rec(N,K,1,1).

binom_rec(_,K,I,R) when K == I - 1 -> R;
binom_rec(N,K,I,R) ->
  binom_rec(N,K,(I+1), ((R*(N-I+1))/(I))).

printer([B|[]]) -> io:fwrite("~*.*.0f~n", [9, 6, B]);
printer([B|BT]) ->
  io:fwrite("~*.*.0f~n", [9, 6, B]),
  printer(BT).
