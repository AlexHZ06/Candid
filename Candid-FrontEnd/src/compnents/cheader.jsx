import { Link } from "react-router-dom"

function CHeader({active}){

    return(

        <div className={`
        
            w-full
            flex
            flex-row
            shadow-[0_0_10px_rgba(0,0,0,0.25)]
            pl-4
            pr-4
            h-15
            items-center
        
        `}>

            <p className="
            
                flex-7
                font-black
                text-3xl
                text-amber-500
                tracking-widest
                
            
            ">Candid</p>
            <p className="
            
                flex-6
                
               
            
            "></p>
            <Link to={"/chome"} className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Home" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Home</Link>
            <Link to={"/profiles"} className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Search" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Search</Link>
            <Link className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Bookings" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Bookings</Link>
            <Link className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Profile" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Profile</Link>

        </div>

    )

}

export default CHeader