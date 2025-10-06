import {
  Alert,
  Avatar,
  Box,
  Button,
  Divider,
  Modal,
  Snackbar,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import EditIcon from "@mui/icons-material/Edit";
import ProfileFildCard from "../../../seller/pages/Account/ProfileFildCard";
import { useAppSelector } from "../../../Redux Toolkit/Store";
import { style } from "../../../seller/pages/Account/Profile";

const UserDetails = () => {
  const { user } = useAppSelector((store) => store);
  

  return (
    <div className="flex justify-center py-10">
      <div className="w-full lg:w-[70%]  ">
        <div className="flex items-center pb-3 justify-between">
          <h1 className="text-2xl font-bold text-gray-600 ">
            Persional Details
          </h1>
          
        </div>
        <div className="space-y-5">
        
          <div>
            <ProfileFildCard keys={"Name"} value={user.user?.fullName} />
            <Divider />
            <ProfileFildCard keys={"Email"} value={user.user?.email} />
            <Divider />
            <ProfileFildCard keys={"Mobile"} value={user.user?.mobile} />
          </div>
        </div>
      </div>
     
    
    </div>
  );
};

export default UserDetails;
