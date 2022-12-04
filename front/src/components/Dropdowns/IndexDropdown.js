import React from "react";
import { Link } from "react-router-dom";
import { createPopper } from "@popperjs/core";
import { useSelector } from "react-redux";

const IndexDropdown = () => {

  const { user } = useSelector(({ user }) => ({ user: user.user }));
  const item = localStorage.getItem("user");
  let role = "";
  if (item != null) {
    role = JSON.parse(item).role;
  }

  // dropdown props
  const [dropdownPopoverShow, setDropdownPopoverShow] = React.useState(false);
  const btnDropdownRef = React.createRef();
  const popoverDropdownRef = React.createRef();
  const openDropdownPopover = () => {
    createPopper(btnDropdownRef.current, popoverDropdownRef.current, {
      placement: "bottom-start",
    });
    setDropdownPopoverShow(true);
  };
  const closeDropdownPopover = () => {
    setDropdownPopoverShow(false);
  };

  return (
    <>
      <a
        className="hover:text-blueGray-500 text-blueGray-700 px-3 py-4 lg:py-2 flex items-center text-xs uppercase font-bold"
        href="components/Dropdowns/IndexDropdown#pablo"
        ref={btnDropdownRef}
        onClick={(e) => {
          e.preventDefault();
          dropdownPopoverShow ? closeDropdownPopover() : openDropdownPopover();
        }}
      >
        menu Pages
      </a>
      <div
        ref={popoverDropdownRef}
        className={
          (dropdownPopoverShow ? "block " : "hidden ") +
          "bg-white text-base z-50 float-left py-2 list-none text-left rounded shadow-lg min-w-48"
        }
      >
        {role == "ROLE_ADMIN" &&
          <span
            className={
              "text-sm pt-2 pb-0 px-4 font-bold block w-full whitespace-nowrap bg-transparent text-blueGray-400"
            }
          >
            Admin Layout
          </span>
        }
        {role == "ROLE_ADMIN" &&
          <Link
            to="/admin/dashboard"
            className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          >
            Dashboard
          </Link>
        }
        {role == "ROLE_ADMIN" &&
          <Link
            to="/admin/settings"
            className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          >
            Settings
          </Link>
        }
        {role == "ROLE_ADMIN" &&
          <Link
            to="/admin/tables"
            className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          >
            Tables
          </Link>
        }
        {role == "ROLE_ADMIN" &&
          <Link
            to="/admin/maps"
            className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          >
            Maps
          </Link>
        }
        {!user &&
          <div className="h-0 mx-4 my-2 border border-solid border-blueGray-100" />
        }
        {!user &&
          <span
            className={
              "text-sm pt-2 pb-0 px-4 font-bold block w-full whitespace-nowrap bg-transparent text-blueGray-400"
            }
          >
            Auth Layout
          </span>
        }
        {!user &&
          <Link
            to="/auth/login"
            className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          >
            Login
          </Link>
        }
        {!user ?
          <Link
            to="/auth/register"
            className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          >
            Register
          </Link>
          : null}


        <div className="h-0 mx-4 my-2 border border-solid border-blueGray-100" />
        <span
          className={
            "text-sm pt-2 pb-0 px-4 font-bold block w-full whitespace-nowrap bg-transparent text-blueGray-400"
          }
        >
          No Layout
        </span>
        <Link
          to="/landing"
          className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
        >
          Landing
        </Link>
        <Link
          to="/profile"
          className="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
        >
          Profile
        </Link>
      </div>
    </>
  );
};

export default IndexDropdown;
