import http from 'k6/http';

export let options = {
  scenarios: {
    default: {
      executor: 'constant-arrival-rate',
      duration: '5s',
      rate: 2, timeUnit: '1s',
      preAllocatedVUs: 1
    }
  }
};

export default function () {

  const url = 'http://host.docker.internal:8080';
  const data = {id: 12, userId: 22, theme: "t", body: "b"}

  // hello
  http.get(url);

  // hello name
  http.get(url + '/hello?name=Eugene');

  // makePost
   http.get(url, JSON.stringify(data), {
      headers: { 'Content-Type': 'application/json' },
   });

   //postId
  http.get(url + '/post/11');
  http.get(url + '/post/12');

};