import { Link } from "react-router-dom"

function PHeader({active}){

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
            <Link to={"/Phome"} className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Home" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Home</Link>
            <Link to={"/gallery"} className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Gallery" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Gallery</Link>
            <Link className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Calander" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Calander</Link>
            <Link className={`
            
                flex-1
                active:text-neutral-500
                transition
                duration-100
                ${active === "Clients" ? "text-black" : "text-neutral-500 hover:text-black"}
            `}
            >Clients</Link>
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

export default PHeader