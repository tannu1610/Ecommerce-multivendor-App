// import {
//   configureStore,
//   combineReducers,
// } from "@reduxjs/toolkit";

// import { thunk } from "redux-thunk";
// import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
// import sellerSlice from "./Seller/sellerSlice";
// import sellerAuthenticationSlice from "./Seller/sellerAuthenticationSlice";
// import sellerProductSlice from "./Seller/sellerProductSlice";
// import ProductSlice from "./Customer/ProductSlice";
// import CartSlice from "./Customer/CartSlice";
// import AuthSlice from "./Customer/AuthSlice";
// import UserSlice from "./Customer/UserSlice";
// import OrderSlice from "./Customer/OrderSlice";
// import sellerOrderSlice from "./Seller/sellerOrderSlice";
// import payoutSlice from "./Seller/payoutSlice";
// import transactionSlice from "./Seller/transactionSlice";
// import CouponSlice from "./Customer/CouponSlice";
// import AdminCouponSlice from "./Admin/AdminCouponSlice";
// import ReviewSlice from "./Customer/ReviewSlice";
// import WishlistSlice from "./Customer/WishlistSlice";
// import AiChatBotSlice from "./Customer/AiChatBotSlice";
// import revenueChartSlice from "./Seller/revenueChartSlice";
// import CustomerSlice from "./Customer/Customer/CustomerSlice";
// import DealSlice from "./Admin/DealSlice";
// import AdminSlice from "./Admin/AdminSlice";

// const rootReducer = combineReducers({
  
//   // customer
//   auth: AuthSlice,
//   user: UserSlice,
//   products: ProductSlice,
//   cart: CartSlice,
//   orders: OrderSlice,
//   coupone: CouponSlice,
//   review: ReviewSlice,
//   wishlist: WishlistSlice,
//   aiChatBot: AiChatBotSlice,
//   homePage:CustomerSlice,

//   // seller
//   sellers: sellerSlice,
//   sellerAuth: sellerAuthenticationSlice,
//   sellerProduct: sellerProductSlice,
//   sellerOrder: sellerOrderSlice,
//   payouts: payoutSlice,
//   transaction: transactionSlice,
//   revenueChart: revenueChartSlice,

//   // admin
//   adminCoupon:AdminCouponSlice,
//   adminDeals:DealSlice,
//   admin:AdminSlice,
//   deal:DealSlice
// });

// const store = configureStore({
//   reducer: rootReducer,
//   middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
// });
// export type AppDispatch = typeof store.dispatch;
// export type RootState = ReturnType<typeof rootReducer>;

// export const useAppDispatch = () => useDispatch<AppDispatch>();
// export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

// export default store;








// src/ReduxToolkit/Store.ts

import { configureStore, combineReducers } from "@reduxjs/toolkit";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";

// 🧩 Customer slices
import AuthSlice from "./Customer/AuthSlice";
import UserSlice from "./Customer/UserSlice";
import ProductSlice from "./Customer/ProductSlice";
import CartSlice from "./Customer/CartSlice";
import OrderSlice from "./Customer/OrderSlice";
import CouponSlice from "./Customer/CouponSlice";
import ReviewSlice from "./Customer/ReviewSlice";
import WishlistSlice from "./Customer/WishlistSlice";
import AiChatBotSlice from "./Customer/AiChatBotSlice";
import CustomerSlice from "./Customer/Customer/CustomerSlice";

// 🧩 Seller slices
import sellerSlice from "./Seller/sellerSlice";
import sellerAuthenticationSlice from "./Seller/sellerAuthenticationSlice";
import sellerProductSlice from "./Seller/sellerProductSlice";
import sellerOrderSlice from "./Seller/sellerOrderSlice";
import payoutSlice from "./Seller/payoutSlice";
import transactionSlice from "./Seller/transactionSlice";
import revenueChartSlice from "./Seller/revenueChartSlice";

// 🧩 Admin slices
import AdminSlice from "./Admin/AdminSlice";
import AdminCouponSlice from "./Admin/AdminCouponSlice";
import DealSlice from "./Admin/DealSlice";

const rootReducer = combineReducers({
  // Customer
  auth: AuthSlice,
  user: UserSlice,
  products: ProductSlice,
  cart: CartSlice,
  orders: OrderSlice,
  coupon: CouponSlice,
  review: ReviewSlice,
  wishlist: WishlistSlice,
  aiChatBot: AiChatBotSlice,
  homePage: CustomerSlice,

  // Seller
  sellers: sellerSlice,
  sellerAuth: sellerAuthenticationSlice,
  sellerProduct: sellerProductSlice,
  sellerOrder: sellerOrderSlice,
  payouts: payoutSlice,
  transaction: transactionSlice,
  revenueChart: revenueChartSlice,

  // Admin
  admin: AdminSlice,
  adminCoupon: AdminCouponSlice,
  adminDeals: DealSlice, // ✅ Only one reference to DealSlice
});

const store = configureStore({
  reducer: rootReducer,
  // No need to specify middleware unless you're adding custom ones
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof rootReducer>;

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;

export default store;
