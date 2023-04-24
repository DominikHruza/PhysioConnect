import { createAction } from '@ngrx/store';

export const LOADING_START = '[LOADING] Loading Start';
export const LOADING_END = '[LOADING] Loading End';

export const loadingStart = createAction(
  LOADING_START,
);

export const loadingEnd = createAction(
  LOADING_END,
);
