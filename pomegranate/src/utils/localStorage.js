export function fetch(STORAGE_KEY) {
  let value = window.localStorage.getItem(STORAGE_KEY)
  if (value) {
    return value
  } else {
    return null;
  }
}

export function save(STORAGE_KEY, items) {
  window.localStorage.setItem(STORAGE_KEY, JSON.stringify(items))
}

export function remove(STORAGE_KEY) {
  window.localStorage.removeItem(STORAGE_KEY)
}
