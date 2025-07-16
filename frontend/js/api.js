// api.js
const API_URL = 'http://localhost:8080/api';
const AUTH_USER = 'admin';
const AUTH_PASS = 'admin';

function getAuthHeader() {
  return 'Basic ' + btoa(AUTH_USER + ':' + AUTH_PASS);
}

async function apiFetch(url, options = {}) {
  options.headers = options.headers || {};
  options.headers['Authorization'] = getAuthHeader();
  options.headers['Content-Type'] = 'application/json';
  const resp = await fetch(url, options);
  if (!resp.ok) {
    const err = await resp.json().catch(() => ({}));
    throw new Error(err.error || 'Erro na requisição');
  }
  return resp.json();
}

window.apiFetch = apiFetch; 