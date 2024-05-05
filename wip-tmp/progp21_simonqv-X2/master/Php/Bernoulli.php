
<?php
    $res = bernoulli(19);
    foreach ($res as &$value) {
      echo number_format((float) $value, 6, '.', '');
      echo "\r\n";
    }


  function binomial($n, $k) {
    $r = 1;
    for ($i = 1; $i <= $k; $i++) {
      $r = ($r * ($n - $i + 1))/$i;
    }
    return $r;
  }

  function bernoulli($n) {
    $ar = array(
      1 => 1.0
    );

    for ($m = 1; $m <= $n; $m++) {
      $bm = 0.0;
      for ($k = 0; $k <= $m - 1; $k++) {
        $bm = $bm - binomial($m + 1, $k) * $ar[$k + 1];
      }
      $bm = $bm / ($m + 1);
      $ar[$m + 1] = $bm;
    }
    return $ar;
  }

?>
