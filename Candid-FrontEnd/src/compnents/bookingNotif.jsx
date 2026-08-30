import { Link } from "react-router-dom"

function BookingNotif(){

    return(

        <Link className="
        
            outline
            flex
            flex-row
            w-9/10
            h-20
            shrink-0
            items-center
            pl-5
            rounded-xl
            bg-neutral-500
            font-semibold
            text-white
            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
            hover:bg-neutral-600
            transition
            duration-100
            hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
            active:bg-neutral-500
            active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
            
        
        ">
            <p className="
            
                flex-1
            
            ">Mark James</p>
            <p className="
            
                flex-3
            
            ">26/04/2026</p>
            <p className="
            
                flex-1
            
            ">Status: Pending</p>
        </Link>

    )

}

export default BookingNotif