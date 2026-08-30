import { Link } from "react-router-dom"

function MessageNotif(){

    return(

        <div className="
        
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
            
        
        ">
            <p className="
            
                flex-1
            
            ">Mark James</p>
            <p className="
            
                flex-3
            
            ">26/04/2026</p>
            <p className="
            
                flex-1
            
            ">Message: hi was just wondering on the...</p>
            <Link className="
            
                bg-white
                w-1/10
                rounded-xl
                flex
                justify-center
                items-center
                h-8
                text-black
                mr-5
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]

                active:shadow-[0_0px_3px_rgba(0,0,0,0.5)_inset]
            
            ">Go To</Link>
        </div>

    )

}

export default MessageNotif