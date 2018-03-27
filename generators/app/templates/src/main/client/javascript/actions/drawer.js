export function openDrawer(name) {
  return {
    type: 'OPEN_DRAWER',
    name,
  };
}

export function closeDrawer() {
  return {
    type: 'CLOSE_DRAWER',
  };
}
