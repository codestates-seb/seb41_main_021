export const f = (diff, n, count) => {
  if (diff < n) {
    return count;
  }
  count++;
  return f(diff, n * 2, count);
};
