import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 10,
    duration: '30s',
};
// k6 run --out influxdb=http://127.0.0.1:8086/k6 PerformanceTest.js

export default function () {
    let res = http.get('http://localhost:5113/api/orders');
    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time < 200ms': (r) => r.timings.duration < 200,
    });
    sleep(1);
}
