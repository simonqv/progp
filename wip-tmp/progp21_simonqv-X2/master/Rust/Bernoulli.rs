fn main() {
	let size = 20;
    let vec = bernoulli(size);
    for i in 0..(size - 1) {
    	let val = vec.get(i as usize).unwrap();
    	
    	if (val.abs() as f64) < ((f64::powf(10.0,-8.0)) as f64) {
    		println!("{}", 0.0);
    	} else {
    		println!("{}", val);	
    	}
    }
}


fn bernoulli(n: u32) -> Vec<f64> {
	let mut vec = Vec::new();
	vec.push(1.0);
	for m in 1..n {
		let mut bm = 0.0;
		for k in 0..(m) {
			bm = bm - (binom(m+1, k) * vec.get(k as usize).unwrap());
		}
		bm = bm / (m as f64 + 1.0);
		vec.push(bm);
	}
	return vec;
}

fn binom(n: u32, k: u32) -> f64 {
	let mut r = 1.0;
	for i in 1..(k+1) {
		r = r * (n as f64 - i as f64 + 1.0) / i as f64;
	}
	return r;
}